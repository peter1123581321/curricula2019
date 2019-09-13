package masterarbeit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import masterarbeit.entities.JobEntity;
import masterarbeit.repositories.JobRepository;

@Service
public class JobImporter {

	@Autowired
	private JobRepository jobRepository;

	public void importJobs(String path) {
		try {
			File directory = new File(path);
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
//					System.out.println(fileContent.length());
					JobEntity jobEntity = new JobEntity(fileContent);
					jobRepository.save(jobEntity);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
