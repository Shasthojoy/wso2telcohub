Incident: MIFE-538
Steps to install

1. Replace 'mavenproject1-1.0-SNAPSHOT.war' in <IS_HOME>/repository/deployment/server/webapps with the mavenproject1-1.0-SNAPSHOT.war app provided in the patch
2. Replace following files with the files provided in the patch
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/jaggery.conf
	<AM_HOME>/repository/deployment/server/jaggeryapps/manage/site/pages/error-pages/error.html

	<AM_HOME>/repository/deployment/server/jaggeryapps/publisher/jaggery.conf
	<AM_HOME>/repository/deployment/server/jaggeryapps/publisher/site/pages/error-pages/error.html

	<AM_HOME>/repository/deployment/server/jaggeryapps/store/jaggery.conf
	<AM_HOME>/repository/deployment/server/jaggeryapps/store/site/pages/error-pages/error.html

	<IS_HOME>/repository/deployment/server/jaggeryapps/dashboard/jaggery.conf
	<IS_HOME>/repository/deployment/server/jaggeryapps/dashboard/site/error-pages/401.html
	<IS_HOME>/repository/deployment/server/jaggeryapps/dashboard/site/error-pages/403.html
	<IS_HOME>/repository/deployment/server/jaggeryapps/dashboard/site/error-pages/404.html
	<IS_HOME>/repository/deployment/server/jaggeryapps/dashboard/site/error-pages/500.html
	<IS_HOME>/repository/deployment/server/jaggeryapps/dashboard/site/error-pages/error.html

NOTE --------------------->
Changes in mavenproject1-1.0-SNAPSHOT.war related to the MIFE-535 also add in this patch

