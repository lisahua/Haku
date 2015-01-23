package syntacticAnalysis;

import org.testng.annotations.Test;

import seal.haku.empirical.logProcessing.AnomalyCalculator;
import seal.haku.empirical.logProcessing.AnomalyFileTracer;
import seal.haku.empirical.logProcessing.CorrelationAccumulator;
import seal.haku.empirical.logProcessing.LifeSpanDetector;
import seal.haku.empirical.logProcessing.ProjectAccumulator;

/**
 * @Author Lisa
 * @Date: Jan 22, 2015
 */
public class TestLogProcessing {

	String outputFolder = "/Users/admin/Documents/nameExample/workFolder/outputResult/";
	String[] projectName = { "abdera-abdera-1.0", "activemq-activemq-4.0",
			"ambari-release-1.2.0", "any23-any23-0.3.0", "accumulo-1.3.5",
			"airavata-airavata-0.5", "ant-ANT_15_FINAL", "archiva-archiva-1.3",
			"aries-0.4", "avro-release-1.5.0", "batik-batik_1_0",
			"bigtop-release-0.6.0", "camel-camel-2.0.0",
			"cassandra-cassandra-2.0.0", "chainsaw-1_2_1",
			"chukwa-chukwa-0.5.0", "click-click-2.2.0", "cayenne-3.0-final",
			"chainsaw-1_2_1", "cloudstack-2.2.0",
			"commons-collections-COLLECTIONS_3_0", "commons-io-IO_1_0",
			"commons-jexl-COMMONS_JEXL-1_1", "commons-lang-LANG_3_0",
			"commons-math-MATH_2_0", "commons-net-NET_3_0",
			"commons-vfs-vfs-1.0", "continuum-continuum-1.0", "couchdb-1.0.0",
			"curator-1.0.0", "cxf-cxf-2.3.0", "deltacloud-release-1.0.0",
			"deltaspike-deltaspike-project-0.4", "esme-apache-esme-1.2",
			"felix-3fc86e23a283d0baee26de15d427a7866155151a",
			"flume-flume-1.2.0", "fop-fop-1_0", "forrest-forrest_06",
			"giraph-release-0.1.0", "gora-gora-0.2", "hadoop-release-1.0.0",
			"hama-0.2-RC2", "hbase-0.90.0", "hive-release-0.5.0",
			"isis-isis-1.0.0", "jackrabbit-2.0.0", "james-2_3_2",
			"jclouds-jclouds-1.0.0", "jena-jena-2.7.X", "kafka-0.8.0",
			"knox-0.2.0-branch", "lenya-RELEASE_2_0", "libcloud-0.4.0",
			"log4j-1_2_16", "lucene-solr-lucene_solr_3_3",
			"lucy-apache-lucy-incubating-0.2.0", "mahout-mahout-0.1",
			"manifoldcf-release-1.0", "marmotta-import", "maven-maven-3.0",
			"mesos-0.10.0", "mina-1.0.0", "mrunit-release-0.5.0-incubating",
			"myfaces-2_0_0", "nutch-release-1.0", "ode-APACHE_ODE_1.2",
			"ofbiz-REL-4.0", "oodt-0.2", "openjpa-1.0.0",
			"opennlp-asf_migration", "openoffice-AOO340.zip.crdownload",
			"pdfbox-1.0.0", "phoenix-2.2.3", "pig-release-0.1.0", "pivot-1.0",
			"poi-REL_3_0", "qpid-0.5", "rave-0.10", "river-2.1.1",
			"roller-roller_2.0", "servicemix-servicemix-5.0.0",
			"shindig-shindig-project-1.0-incubating", "shiro-1.2.0",
			"sling-sling-13", "solr-release-1.1.0",
			"stanbol-stanbol-parent-1-incubating", "stratos-3.0.0-incubating",
			"struts-STRUTS_2_1_0", "syncope-syncope-0.2", "tajo-release-0.2.0",
			"tez-release-0.2.0-rc0", "thrift-0.2.0", "tika-0.7",
			"tiles-tiles-2.0.0", "tomee-tomee-1.0.0", "vysper-0.5",
			"whirr-release-0.7.0", "wicket-wicket-1.5.0",
			"xmlbeans-xmlbeans-1-0-0", "zookeeper-release-3.0.0", };

	// @Test
	public void testLogProcessing() {
		AnomalyCalculator calc = new AnomalyCalculator();
		for (String name : projectName) {
			calc.readFile(outputFolder + name + ".log");
		}
	}

	// @Test
	public void testUPFileTracer() {

		AnomalyFileTracer tracer;
		for (String name : projectName) {
			tracer = new AnomalyFileTracer(outputFolder + name
					+ ".methodAnomal", outputFolder + name
					+ ".identifierAnomal");
			tracer.readFile(outputFolder + name + ".upi");
		}
	}

	// @Test
	public void testCorrelationFile() {
		CorrelationAccumulator correlator = new CorrelationAccumulator();
		for (String name : projectName) {
			correlator.correlatePNBug(outputFolder + name);
		}
	}

	// @Test
	public void testProjectCorrelation() {
		ProjectAccumulator projectAc = new ProjectAccumulator(outputFolder
				+ "allProject.correlate");
		for (String project : projectName) {
			projectAc.readFile(outputFolder + project);
		}
	}

	@Test
	public void testLifeSpanCollector() {
		LifeSpanDetector lifeSpan = new LifeSpanDetector();
		lifeSpan.detectPreservedFiles(outputFolder + "snapShotFile.txt",
				outputFolder + "latestNames.txt", outputFolder
						+ "allProjectFile.txt");
	}
}