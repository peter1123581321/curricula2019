<?php

	$POPULATION_SIZE = 100;
	$MAXITERATIONS = 100;
	$MAXMUTATIONS = 20;
	$CLOUDBORDER = 3.813320802444695;

	$db=mysqli_connect("localhost","root","");	
	mysqli_set_charset($db,"utf8");
	mysqli_select_db($db,"dewiki");
	
	mysqli_query($db,"truncate genetic_mod2;");
	
	$courses = array();
	loadCourses();
	echo "courses: ".sizeof($courses)."\n";
	
	$curricula = array();
	loadCurricula();
	echo "curricula: ".sizeof($curricula)."\n";
	
	$scores = array();
	loadScores();
	echo "scores: ".sizeof($scores)."\n";

	foreach ($curricula as $key=>$curriculum) {
		// echo "$key\n";
		/*
		if ($key!=3) {
		 			continue;
		}
		*/
		processCurriculum($key,$curriculum);
		// break;
	}
	
	mysqli_close($db);
	echo "done, bye!\n";

	function processCurriculum($key,$curriculum) {
		global $MAXITERATIONS;
		// $iterationScores = array();
		echo "================= START NEW CURRICULUM\n";
		echo "processing curriculum: ".$curriculum["id"] ." (".$key.")\n";
		$original_individual = buildOriginalIndividual($curriculum);
		// printIndividual($original_individual);
		// echo "================= INITIALISE POPULATION\n";
		$population = buildPopulation($original_individual);
		// printPopulation($population);
		
		insertIterationResult($curriculum,0,$original_individual);
		
		// $iterationScores[] = array(getScoreOfIndividual($original_individual),getLengthOfIndividual($original_individual));

		
		$lengthOriginalIndividual = getLengthOfIndividual($original_individual);
		
		for ($i=1;$i<=$MAXITERATIONS;$i++) {
			 // echo "================= ITERATION $i\n";
			
			// echo "===== RECOMBINATION\n";
			$recombinations = generateRecombinations($population);
			// printPopulation($recombinations);
			// echo "===== MUTATION\n";
			$mutations = generateMutations($population, $original_individual);
			// printPopulation($mutations);
			// echo "===== MERGE RECOMBINATIONS AND MUTATIONS\n";
			$population = array_merge($population,$recombinations,$mutations);
			// printPopulation($population);
			 // echo "===== SELECTION\n";
			$population = deleteInvalidIndividuals($population,$original_individual,$lengthOriginalIndividual);
			$population = selectPopulation($population);
			// printPopulation($population);
			
			// $iterationScores[] = array(getScoreOfIndividual($population[0]),getLengthOfIndividual($population[0]));
			insertIterationResult($curriculum,$i,$population[0]);
		}
		echo "================= FINISHED\n";
		// print_r($iterationScores);
	}

	function deleteInvalidIndividuals($population,$original_individual,$lengthOriginalIndividual) {
		global $POPULATION_SIZE;
		for ($i=$POPULATION_SIZE;$i<sizeof($population);$i++) {
			if (countNumberOfMutations($population[$i], $original_individual)>5 || getLengthOfIndividual($population[$i])<$lengthOriginalIndividual*0.75) {
				array_splice($population,$i,1);
				$i--;
			}
		}
		return $population;
	}
	
	function insertIterationResult($curriculum,$iteration,$individual) {
		global $db;
		$totalScore = getScoreOfIndividual($individual);
		$cloudScore = getCloudOfIndividual($individual);
		$length = getLengthOfIndividual($individual);
		
		// print_r($individual);
		
		$query = "insert into genetic_mod2 (curriculum, iteration, individual, textlength, totalscore, cloudscore) values ('".$curriculum["id"]."',$iteration,'".getIndividualAsString($individual)."',$length,$totalScore,$cloudScore);";
			
		// echo $query . "\n";
		mysqli_query($db,$query);
	}
	
	function generateRecombinations($population) {		
		shuffle($population);
		$recombinations = array();
		for ($i=0;$i<sizeof($population);$i=$i+2) {
			$elder1 = $population[$i];
			$elder2 = $population[$i+1];			
			$recombination = generateRecombination($elder1,$elder2);
			$recombinations[] = $recombination;
		}
		return $recombinations;
	}
	
	function generateRecombination($elder1, $elder2) {
		$child = array_intersect($elder1, $elder2);		
		$remaining = array_diff($elder1, $elder2);
		$remaining = array_merge($remaining,array_diff($elder2, $elder1));	
		shuffle($remaining);
		while(sizeof($child)<sizeof($elder1)) {
			$child[]=array_pop($remaining);
		}
		sort($child);
		return $child;
	}
	
	function generateMutations($population,$original_individual) {
		global $MAXMUTATIONS;
		$mutations = array();
		
		$selectedIndividualIndizesForMutation = array();
		
		while (sizeof($mutations)<$MAXMUTATIONS) {
			$selectedIndividualIndexForMutation = rand(0,sizeof($population)-1);
			if (in_array($selectedIndividualIndexForMutation,$selectedIndividualIndizesForMutation)) {
				continue;
			}
			$selectedIndividualIndizesForMutation[]=$selectedIndividualIndexForMutation;
			$selectedIndividualForMutation = $population[$selectedIndividualIndexForMutation];
			$mutation = generateMutation($selectedIndividualForMutation, $original_individual);
			$mutations[]=$mutation;
		}
		return $mutations;
	}
	
	function selectPopulation($population) {
		global $POPULATION_SIZE;

		/*		
		foreach ($population as $individual) {
			$score = getScoreOfIndividual($individual);
			echo $score."-->";
			printIndividual($individual);
		}
		*/
		
		usort($population,"compareIndividuals");
		
		/*
		echo "==\n";
		
		foreach ($population as $individual) {
			$score = getScoreOfIndividual($individual);
			echo $score."-->";
			printIndividual($individual);
		}
		*/
	
		$population = array_slice($population,0,$POPULATION_SIZE);
			
		return $population;
	}
	
	function compareIndividuals($i1, $i2) {
		$score1 = getScoreOfIndividual($i1);
		$score2 = getScoreOfIndividual($i2);
		if ($score1 == $score2) {
			return 0;
		}
		return ($score1 < $score2) ? 1 : -1;
	}
	
	function getLengthOfIndividual($individual) {
		$length = 0;
		foreach ($individual as $course) {
			$length+=getLengthOfCourse($course);
		}
		return $length;
	}
	
	function getLengthOfCourse($course) {
		global $courses;
		foreach ($courses as $c) {
			if ($c["course"]==$course) {
				return $c["length"];
			}
		}
	}
	
	function getCloudOfIndividual($individual) {
		$cloud = 0;
		foreach ($individual as $course) {
			$cloud+=getCloudOfCourse($course);
		}
		return $cloud/sizeof($individual);
	}
	
	function getCloudOfCourse($course) {
		global $courses;
		foreach ($courses as $c) {
			if ($c["course"]==$course) {
				return $c["cloud"];
			}
		}	
	}
	
	function getScoreOfIndividual($individual) {
		$score = 0;
		foreach ($individual as $course) {
			$score+=getScoreOfCourse($course);
		}
		return $score/sizeof($individual);
	}
	
	function getScoreOfCourse($course) {
		global $scores;
		foreach ($scores as $score) {
			if ($score["course"]==$course) {
				return $score["score"];
			}
		}
		return 0;
	}
	
	function printPopulation($population) {
		foreach ($population as $individual) {
			printIndividual($individual);
		}
	}
	
	function buildPopulation($original_individual) {
		global $POPULATION_SIZE;
		$population = array();
		$population[] = $original_individual;
		
		while (sizeof($population)<$POPULATION_SIZE) {
			$generatedRandomIndividual = generateMutation($original_individual, $original_individual);	
			if (!populationContainsIndividual($population,$generatedRandomIndividual)) {
				$population[] = $generatedRandomIndividual;
			}
		}
		return $population;
	}
	
	function populationContainsIndividual($population, $individual) {
		foreach ($population as $populationIndividual) {
			if ($populationIndividual == $individual) {
				return true;
			}
		}
		return false;
	}
	
	function generateMutation($mutation, $original_individual) {
		global $courses, $CLOUDBORDER;

		$countNumberOfMutations = countNumberOfMutations($mutation, $original_individual);

		if ($countNumberOfMutations<5) {
			$indexOfRemovedCourse = rand(0,sizeof($mutation)-1);
			$removedCourse = array_splice($mutation,$indexOfRemovedCourse,1)[0];
		} else {
			$mutatedCourses=array_values(array_diff($mutation,$original_individual));
			$indexOfRemovedCourse=rand(0,sizeof($mutatedCourses)-1);
			$removedCourse=array_splice($mutation,array_search($mutatedCourses[$indexOfRemovedCourse],$mutation),1)[0];
		}

		$lengthRemovedCourse = getLengthOfCourse($removedCourse);
		
		// printIndividual($mutation);

		$cloudprobability = rand(0,2);

		$countProbability = 0;

		while(true) {
			$indexOfRandomCourse = rand(0,sizeof($courses)-1);
			
			$randomCourse = $courses[$indexOfRandomCourse];
			$randomCourseId = $randomCourse["course"];

			// echo getLengthOfCourse($randomCourseId)."-".$lengthRemovedCourse."\n";

			// pruefen ob neuer course nicht gleich dem entfernten ist bzw. nicht im course vorkommt
			if ($randomCourseId!=$removedCourse && !in_array($randomCourseId,$mutation) && getLengthOfCourse($randomCourseId)>=$lengthRemovedCourse*0.75) {

				if ($countProbability<10 && !($cloudprobability<=1 && getCloudOfCourse($randomCourseId)<=$CLOUDBORDER 
					|| $cloudprobability>1 && getCloudOfCourse($randomCourseId)>$CLOUDBORDER
					)) {
						$countProbability++;
						continue;
					}

				// echo ($cloudprobability<=1?1:0);
				// $clouddistance = getCloudOfCourse($randomCourseId);			
				// echo $randomCourseId." --> " . $clouddistance . " --> ".(getCloudOfCourse($randomCourseId)<=$CLOUDBORDER?1:0)."\n";

				$mutation[]=$randomCourseId;
				break;
			}
		}
		sort($mutation);
		return $mutation;
	}

	function countNumberOfMutations($mutatedIndividual,$original_individual) {
		$count = 0;
		foreach ($mutatedIndividual as $id) {
			if (!in_array($id, $original_individual)) {
				$count++;
			}
		}
		return $count;
	}
	
	function printIndividual($individual) {
		echo getIndividualAsString($individual)."\n";
	}
	
	function getIndividualAsString($individual) {
		$output = "";
		foreach ($individual as $id) {
			$output.=$id.";";
		}	
		return $output;
	}
	
	function buildOriginalIndividual($curriculum) {
		global $courses;
		$individual = array();
		foreach ($courses as $course) {			
			if ($course["curriculum"]==$curriculum["id"])  {
				$individual[] = $course["course"];
			}
		}
		return $individual;
	}
	
	function loadCurricula() {
		global $db, $curricula;
		$query="select curricula,count(*) from curricula group by curricula having count(*)>=20;";
		$result = mysqli_query($db,$query);
		while($row=mysqli_fetch_assoc($result)) {
			$id=$row["curricula"];
			$curricula[] = array("id"=>$id);
		}		
	}
	
	function loadCourses() {
		global $db, $courses;
		$query="select c.id, c.curricula, u.title,c.title, length(c.text) length, avg((1-gwc.salience)*(wps.cloudcomputing+1)) cloudcomputing from googlewordcurricula gwc, googlesearch gs, googlesearchunique gsu, wplinkstructure wps, curricula c, curricula2 u where gwc.idunique=gs.idunique and gs.iduniquesearch=gsu.id and gsu.page_id=wps.page_id and gsu.page_id is not null and c.id=gwc.curricula and c.curricula=u.curricula and c.curricula in (select curricula from curricula group by curricula having count(*)>=20) group by gwc.curricula order by gwc.curricula;";
		
		
		$result=mysqli_query($db,$query);
		while($row=mysqli_fetch_assoc($result)) {
			$course=$row["id"];
			$curriculum=$row["curricula"];
			$length=$row["length"];
			$cloud=$row["cloudcomputing"];
			$courses[] = array("course"=>$course,"curriculum"=>$curriculum,"length"=>$length,"cloud"=>$cloud);
		}
	}
	
	function loadScores() {
		global $db, $scores;
		$query="select course_id id, avg(cosinus_koeffizient_normalisiert) score from auswertung where course_id in (select id from curricula where curricula in (select curricula from curricula group by curricula having count(*)>=20)) group by course_id;";
		$result=mysqli_query($db,$query);
		while($row=mysqli_fetch_assoc($result)) {
			$course=$row["id"];
			$score=$row["score"];
			$scores[] = array("course"=>$course,"score"=>$score);
		}		
	}
?>