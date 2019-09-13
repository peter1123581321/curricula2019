package masterarbeit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import masterarbeit.repositories.DewikiRepository;

@SpringBootApplication
public class App implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(App.class);

	@Autowired
	DewikiRepository dewikiRepository;

	@Autowired
	CurriculaProcessor curriculaProcessor;

	@Autowired
	GoogleProcessor googleProcessor;

	@Autowired
	JobImporter jobImporter;

	@Autowired
	JobProcessor jobProcessor;

	@Autowired
	TestProcessor testProcessor;

	@Autowired
	AuswertungProcessor auswertungProcessor;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(App.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	public void run(String... args) throws Exception {

		// jobImporter.importJobs("");

		// curriculaProcessor.process();

		// jobProcessor.process();

		// googleProcessor.process();

		// googleProcessor.processMissing();

		// auswertungProcessor.processJob2Curricula();

		// auswertungProcessor.processJob2Job();

		// auswertungProcessor.processCurricula2Curricula();

		// testProcessor.process("Woodcutter");
		// testProcessor.process("Wife");
		// testProcessor.process("Hansel");
		// testProcessor.process("Gretel");

	}
}