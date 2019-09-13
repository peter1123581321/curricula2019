<?php
	$db=mysqli_connect("localhost","root","");	
	mysqli_set_charset($db,"utf8");
	mysqli_select_db($db,"dewiki");
		
	/*
	$query="select * from hypothesis1;";
	$result = mysqli_query($db,$query);
	$createstatement="create table hypothesis1_b (page_id int(11), index i_page_id (page_id), ";
	while($row=mysqli_fetch_assoc($result)) {
		$id=$row["page_id"];
		echo "$id\n";
		$createstatement.="c_$id int(11),";
	}
	$createstatement=substr($createstatement,0,strlen($createstatement)-1);
	$createstatement.=") ENGINE = MYISAM;";
	mysqli_query($db,$createstatement);
	echo mysqli_error($db)."\n";
	*/
	
	/*
	$count=1;
	
	$query="select * from hypothesis1 where page_id in (select distinct page_id from googlesearchunique where page_id is not null and is_file=false and is_wikipedia_meta=false order by page_id);";
	$result = mysqli_query($db,$query);
	while($row=mysqli_fetch_assoc($result)) {
		$id=$row["page_id"];
		echo "processing nr: $count, id: $id (" .date("Y-m-d h:i e").")\n";
		
		mysqli_query($db,"create table t_$id ENGINE = MYISAM as select distinct page_id from googlesearchunique where page_id is not null and is_file=false and is_wikipedia_meta=false order by page_id;");
		
		mysqli_query($db,"alter table t_$id add column c_$id int(11);");
		mysqli_query($db,"create index i_page_id on t_$id (page_id);");		
		mysqli_query($db,"create index i_$id on t_$id (c_$id);");				
		mysqli_query($db,"update t_$id set c_$id=-1");
		mysqli_query($db,"update t_$id set c_$id=0 where page_id=$id");
		
		while(true) {
			
			mysqli_query($db,"update t_$id set c_$id=(select max(c_$id)+1 from (select * from t_$id) x)
where c_$id=-1 and page_id in (select l.pl_from from (select * from t_$id) w, pagelinks l where l.pl_to=w.page_id and c_$id = (select max(c_$id) from (select * from t_$id) y));");
		
			$affected_rows=mysqli_affected_rows($db);
			if ($affected_rows<=0) {
				break;
			} else {
				echo "affected rows: $affected_rows (" .date("Y-m-d h:i e").")\n";
			}
		}
		$count++;
	}	
	
	*/

	
	$count=1;
	
	$query="select * from hypothesis1 where page_id in (select distinct page_id from googlesearchunique where page_id is not null and is_file=false and is_wikipedia_meta=false order by page_id);";
	$result = mysqli_query($db,$query);
	while($row=mysqli_fetch_assoc($result)) {
		$id=$row["page_id"];
		echo "processing nr: $count, id: $id (" .date("Y-m-d h:i e").")\n";
		
		$result2 = mysqli_query($db,"select avg((1-gwc.salience)*(t.c_$id+1)) average,std((1-gwc.salience)*(t.c_$id+1)) stddev from googlewordcurricula gwc, googlesearch gs, googlesearchunique gsu, t_$id t where gwc.idunique=gs.idunique and gs.iduniquesearch=gsu.id and gsu.page_id=t.page_id and gsu.page_id is not null and t.c_$id>-1");
		
		$row2 = mysqli_fetch_assoc($result2);
		$avg=$row2["average"];
		$std=$row2["stddev"];
		
		echo "avg: $avg, std: $std\n";
		
		mysqli_query($db,"update hypothesis1 set average=$avg, stddev=$std where page_id=$id");
		
		$count++;
	}	
	
	
	/*
	$count=1;
	
	$query="select * from hypothesis1 where page_id in (select distinct page_id from googlesearchunique where page_id is not null and is_file=false and is_wikipedia_meta=false order by page_id);";
	$result = mysqli_query($db,$query);
	while($row=mysqli_fetch_assoc($result)) {
		$id=$row["page_id"];
		echo "processing nr: $count, id: $id (" .date("Y-m-d h:i e").")\n";
		
		$result2 = mysqli_query($db,"select count(*) anzahl from googlewordcurricula gwc, googlesearch gs, googlesearchunique gsu, t_$id t where gwc.idunique=gs.idunique and gs.iduniquesearch=gsu.id and gsu.page_id=t.page_id and gsu.page_id is not null and t.c_$id>-1");
		
		$row2 = mysqli_fetch_assoc($result2);
		$anzahl=$row2["anzahl"];
		
		echo "anzahl: $anzahl\n";
		
		mysqli_query($db,"update hypothesis1 set anzahl=$anzahl where page_id=$id");
		
		$count++;
	}
	*/
	
	/*
	$count=1;
	
	$query="select * from hypothesis1 where page_id in (select distinct page_id from googlesearchunique where page_id is not null and is_file=false and is_wikipedia_meta=false order by page_id);";
	$result = mysqli_query($db,$query);
	while($row=mysqli_fetch_assoc($result)) {
		$id=$row["page_id"];
		echo "processing nr: $count, id: $id (" .date("Y-m-d h:i e").")\n";
			
		mysqli_query($db,"SET @rowindex := -1;");

		$result2 = mysqli_query($db,"select avg(g.grade) median from
			(select  @rowindex:=@rowindex + 1 AS rowindex, f.grade from (
				select (1-gwc.salience)*(t.c_$id+1) grade from googlewordcurricula gwc, googlesearch gs, googlesearchunique gsu, t_$id t 
				where gwc.idunique=gs.idunique and gs.iduniquesearch=gsu.id and gsu.page_id=t.page_id and gsu.page_id is not null and t.c_$id>-1 ORDER BY (1-gwc.salience)*(t.c_$id+1)
			) f ) g
			where g.rowindex IN (FLOOR(@rowindex / 2) , CEIL(@rowindex / 2));");
		
		$row2 = mysqli_fetch_assoc($result2);
		$median=$row2["median"];
		
		echo "median: $median\n";
		
		mysqli_query($db,"update hypothesis1 set median=$median where page_id=$id");
		
		$count++;
	}	
	*/
	
	mysqli_close($db);
	echo "done, bye!\n";
?>