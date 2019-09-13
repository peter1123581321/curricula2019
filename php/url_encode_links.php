<?php
	$db=mysqli_connect("localhost","root","");
	
	mysqli_set_charset($db,"utf8");
	
	mysqli_select_db($db,"dewiki");
	
	$query="select id,link2 from googlesearchunique where page_id is null and is_file=false and is_wikipedia_meta=false;";
	
	$result = mysqli_query($db,$query);
	
	while($row=mysqli_fetch_assoc($result)) {
		
		$id=$row["id"];
		$original=$row["link2"];
		$decoded = urldecode($original);
		
		echo "$id, $original\t-->\t$decoded\n";
		
		$update_query="update googlesearchunique set link2_decoded='$decoded' where id=$id;";
		
		echo "$update_query\n";
		mysqli_query($db,$update_query);
	}
	
	mysqli_close($db);
	
	?>