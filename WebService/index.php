<?php

try {
    $db_conn = new PDO('mysql:host=localhost;dbname=istok7', 'istok7', 'eewog');
} catch (PDOException $e) {
    echo $e->getMessage();
}

$statement = $db_conn->prepare("SELECT * FROM glucose_data");
$statement->execute();
$result = $statement->fetchAll();

function do_post_request() {
    $url = 'https://fcm.googleapis.com/fcm/send';
    $data = array('to' => 'ejUtG6iYhwc:APA91bGGamAmI3FQwhaVQD7XZG7hlqi_U18QYvMfrfR4V0Y95YK1baQU4aak5IISCkCmLf0dHQFpeLIS6QGo9H81AdmxEOJHLL60c2FwI565iMn_tmDShGfTyb9o-0DBpGNUO87XepBN');

    $options = array(
        'http' => array(
            'header'  => "Content-Type:application/json\r\n".
                            "Authorization:key=AAAA6EDvHUc:APA91bHL78-awEZHZO7Bkh1U0B4ze5PPTPwWBVgyloOwK8BEWu_wWjouGY_7oPcdooaF3cKK9IuLqaaBEC9mVr44t87sArbgCzKYvGuFPrQyxSvDVfiHMTJPU4moLX1xhDYO1ClDEoQ61QXZwGhsGl6qzrWBRpxGtg\r\n",
            'method'  => 'POST',
            'content' => http_build_query($data)
        )
    );

    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    if ($result === FALSE) {
        echo "Error";

    }

}

if (isset($_POST['sendrequest'])) {


    $connection = curl_init();

    $post = ['to' => 'ejUtG6iYhwc:APA91bGGamAmI3FQwhaVQD7XZG7hlqi_U18QYvMfrfR4V0Y95YK1baQU4aak5IISCkCmLf0dHQFpeLIS6QGo9H81AdmxEOJHLL60c2FwI565iMn_tmDShGfTyb9o-0DBpGNUO87XepBN'];
    $data  =json_encode($post);
    curl_setopt($connection, CURLOPT_URL,"https://fcm.googleapis.com/fcm/send");
//    curl_setopt($connection, CURLOPT_POST, true);
    curl_setopt($connection, CURLOPT_HTTPHEADER, array("Content-Type:application/json", "Authorization:key=AAAA6EDvHUc:APA91bHL78-awEZHZO7Bkh1U0B4ze5PPTPwWBVgyloOwK8BEWu_wWjouGY_7oPcdooaF3cKK9IuLqaaBEC9mVr44t87sArbgCzKYvGuFPrQyxSvDVfiHMTJPU4moLX1xhDYO1ClDEoQ61QXZwGhsGl6qzrWBRpxGtg"));
    curl_setopt($connection, CURLOPT_HEADER, true);
    curl_setopt($connection, CURLOPT_POSTFIELDS,$data);

    //curl_setopt($connection, CURLOPT_RETURNTRANSFER, true);

    $server_output = curl_exec ($connection);
    $decoded = json_decode($server_output);
    var_dump($decoded);
    curl_close ($connection);

}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Informacna stranka bakalarksej prace">
    <meta name="author" content="Jaroslav IStok">
    <!--<link rel="icon" href="../../favicon.ico">-->

    <title>Mobilný asisten pre diabetikov</title>

    <!-- Bootstrap core CSS -->
    <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../style.css" rel="stylesheet">

</head>

<body>

<nav class="navbar navbar-fixed-top navbar-dark bg-inverse">
    <a class="navbar-brand" href="#">Bakalárska práca</a>
    <ul class="nav navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="#">Domov<span class="sr-only">(current)</span></a>
        </li>
    </ul>
</nav>

<div class="container">

    <div class="starter-template">
        <h1>Namerané hodnoty cukru v krvi</h1>

        <form method="post">
            <input type="submit" value="Send POST request" name="sendrequest">
        </form>
    </div>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Číslo záznamu</th>
            <th>Kategória</th>
            <th>Hodnota cukru</th>
            <th>Dátum a Čas</th>

        </tr>
        </thead>
        <tbody>
        <?php foreach ($result as $item): ?>
        <tr>
            <td><?= $item['glucose_data_id'] ?></td>
            <td><?= $item['category'] ?></td>
            <td><?= $item['glucose_value'] ?></td>
            <td><?= $item['date'] ?></td>
        </tr>

        <?php endforeach; ?>
        </tbody>
    </table>

</div><!-- /.container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js" integrity="sha384-THPy051/pYDQGanwU6poAc/hOdQxjnOEXzbT+OuUAFqNqFjL+4IGLBgCJC3ZOShY" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js" integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB" crossorigin="anonymous"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
