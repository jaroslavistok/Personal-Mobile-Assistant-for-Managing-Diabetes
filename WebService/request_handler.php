<?php

try {
    $db_conn = new PDO('mysql:host=localhost;dbname=istok7', 'istok7', 'eewog');
} catch (PDOException $e) {
    echo $e->getMessage();
}


$parsed = json_decode($_POST);

if (isset($_POST)) {
    handlePostRequest();
    $statement = $db_conn->prepare('INSERT INTO glucose_data(glucose_value, date, category) VALUES(:glucose, :date, :category)');
    $statement->execute(array(
        ':glucose' => $_POST['glucose'],
        ':date' => $_POST['dateTime'],
        ':category' => $_POST['category']
    ));
    echo 4;
}

function handlePostRequest()
{
    if (isJson()) {
        $jsonEncoded = file_get_contents('php://input');
        $jsonDecoded = json_decode($jsonEncoded, true);

        $values= "";
        if (is_array($jsonDecoded)) {
            foreach ($jsonDecoded as $varName => $varValue) {
                $_POST[$varName] = $varValue;
                $values .= $varName;
            }
            echo 4;
        }
    }
}

function isJson()
{
    return isset($_SERVER['CONTENT_TYPE'])
        and stripos($_SERVER['CONTENT_TYPE'], 'application/json') !== false;
}

echo 2;


