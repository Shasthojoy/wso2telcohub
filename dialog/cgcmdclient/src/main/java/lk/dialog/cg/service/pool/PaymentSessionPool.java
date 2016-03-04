package lk.dialog.cg.service.pool;

//import java.sql.*;
import lk.dialog.cg.service.PaymentService;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

/**
 * A class for preallocating, recycling, and managing
 *
 */
public class PaymentSessionPool implements Runnable {

    private String appId, user, password, rcode, domainId;
    private int maxConnections;
    private boolean waitIfBusy;
    private Vector availableConnections, busyConnections;
    private boolean connectionPending = false;
    private int initialConnections;

    private static final Logger LOG = Logger.getLogger(PaymentSessionPool.class.getName());

    public String url = "/CGWebService.wsdl";
//    public final String url = "/src/main/wsdl/CGWebService.wsdl";

    public PaymentSessionPool(String path, String domain,
                              boolean waitIfBusy)
            throws Exception {

        Properties props = new Properties();
        InputStreamReader in = null;
        try {
            //in =this.getC getResourceAsStream
            in = new InputStreamReader(new FileInputStream(path + "/" + domain + ".properties"), "UTF-8");
            props.load(in);
            this.url = props.getProperty("cg.url");
            this.appId = props.getProperty("cg.appId");
            this.user = props.getProperty("cg.user");
            this.password = props.getProperty("cg.password");
            this.rcode = props.getProperty("cg.rcode");
            this.domainId = props.getProperty("cg.domainId");

            initialConnections = Integer.parseInt(props.getProperty("initconnections"));
            maxConnections = Integer.parseInt(props.getProperty("maxConnections"));

            LOG.debug(domain + " :pool initiallise start" + appId + ":" + user + ":" + password + ":" + rcode + ":" + domainId);
            System.out.println(domain + " :pool initiallise start");

        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            System.err.println(
                    "Check your Property file, it should be in application home dir, Error:"
                            + e.getCause() + "Cant load APPLICATION.properties");
            //System.exit(-1);
        } catch (IOException e) {
            System.err.println(
                    "Check your Property file, it should be in application home dir, Error:"
                            + e.getCause() + "Cant load APPLICATION.properties");
            //System.exit(-1);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }

        this.maxConnections = maxConnections;
        this.waitIfBusy = waitIfBusy;
        if (initialConnections > maxConnections) {
            initialConnections = maxConnections;
        }
        availableConnections = new Vector(initialConnections);
        busyConnections = new Vector();
        for (int i = 0; i < initialConnections; i++) {
            availableConnections.addElement(makeNewConnection());
        }

        LOG.debug(domain + " :pool initiallise completed");
        System.out.println(domain + " :pool initiallise completed");

    }

    public synchronized PaymentService getPaymentsession()
            throws Exception {
        if (!availableConnections.isEmpty()) {
            PaymentService existingConnection =
                    (PaymentService) availableConnections.lastElement();
            int lastIndex = availableConnections.size() - 1;
            availableConnections.removeElementAt(lastIndex);
            // If connection on available list is closed (e.g.,
            // it timed out), then remove it from available list
            // and repeat the process of obtaining a connection.
            // Also wake up threads that were waiting for a
            // connection because maxConnection limit was reached.
            //if (existingConnection.isClosed()) {
            //    notifyAll(); // Freed up a spot for anybody waiting
            //    return (getPaymentsession());
            //} else {
            busyConnections.addElement(existingConnection);
            return (existingConnection);
            //}
        } else {

            // Three possible cases:
            // 1) You haven't reached maxConnections limit. So
            //    establish one in the background if there isn't
            //    already one pending, then wait for
            //    the next available connection (whether or not
            //    it was the newly established one).
            // 2) You reached maxConnections limit and waitIfBusy
            //    flag is false. Throw SQLException in such a case.
            // 3) You reached maxConnections limit and waitIfBusy
            //    flag is true. Then do the same thing as in second
            //    part of step 1: wait for next available connection.

            if ((totalConnections() < maxConnections)
                    && !connectionPending) {
                makeBackgroundConnection();
            } else if (!waitIfBusy) {
                throw new Exception("Connection limit reached");
            }
            // Wait for either a new connection to be established
            // (if you called makeBackgroundConnection) or for
            // an existing connection to be freed up.
            try {
                wait();
            } catch (InterruptedException ie) {
            }
            // Someone freed up a connection, so try again.
            return (getPaymentsession());
        }
    }

    // You can't just make a new connection in the foreground
    // when none are available, since this can take several
    // seconds with a slow network connection. Instead,
    // start a thread that establishes a new connection,
    // then wait. You get woken up either when the new connection
    // is established or if someone finishes with an existing
    // connection.
    private void makeBackgroundConnection() {
        connectionPending = true;
        try {
            Thread connectThread = new Thread(this);
            connectThread.start();
        } catch (OutOfMemoryError oome) {
            // Give up on new connection
        }
    }

    public void run() {
        try {
            PaymentService connection = makeNewConnection();
            synchronized (this) {
                availableConnections.addElement(connection);
                connectionPending = false;
                notifyAll();
            }
        } catch (Exception e) { // SQLException or OutOfMemory
            // Give up on new connection and wait for existing one
            // to free up.
        }
    }

    // This explicitly makes a new connection. Called in
    // the foreground when initializing the PaymentSessionPool,
    // and called in the background when running.
    private PaymentService makeNewConnection()
            throws Exception {
        try {

            //TO-DO.. load property file and create new connection
            PaymentService paymentservice = new PaymentService(url, appId, user, password, rcode, domainId, false);
            // Load database driver if not already loaded
//            Class.forName(driver);
//            // Establish network connection to database
//            PaymentService connection =
//                    DriverManager.getConnection(url, username, password);
            return (paymentservice);
        } catch (Exception cnfe) {
            // Simplify try/catch blocks of people using this by
            // throwing only one exception type.
            throw new Exception(cnfe.getMessage());
        }
    }

    public synchronized void free(PaymentService paymentservice) {
        busyConnections.removeElement(paymentservice);
        availableConnections.addElement(paymentservice);
        // Wake up threads that are waiting for a connection
        notifyAll();
    }

    public synchronized int totalConnections() {
        return (availableConnections.size()
                + busyConnections.size());
    }

    /**
     * Close all the connections. Use with caution: be sure no connections are
     * in use before calling. Note that you are not <I>required</I> to call this
     * when done with a PaymentSessionPool, since connections are guaranteed to
     * be closed when garbage collected. But this method gives more control
     * regarding when the connections are closed.
     */
    public synchronized void closeAllConnections() {
        closeConnections(availableConnections);
        availableConnections = new Vector();
        closeConnections(busyConnections);
        busyConnections = new Vector();
    }

    private void closeConnections(Vector paymentservices) {
        try {
            for (int i = 0; i < paymentservices.size(); i++) {
                PaymentService paymentservice =
                        (PaymentService) paymentservices.elementAt(i);
                if (!paymentservice.isClosed()) {
                    paymentservice.close();
                }
            }
        } catch (Exception sqle) {
            // Ignore errors; garbage collect anyhow
        }
    }

    public synchronized String toString() {
        String info =
                "PaymentSessionPool(" + url + "," + user + ")"
                        + ", available=" + availableConnections.size()
                        + ", busy=" + busyConnections.size()
                        + ", max=" + maxConnections;
        return (info);
    }
}
