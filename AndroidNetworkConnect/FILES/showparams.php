<?php

$res = array();

$res['method'] = $_SERVER['REQUEST_METHOD'];

$res['params'] = array();

foreach ($_REQUEST[] as $key => $value) {
	$res['params'][$key] = $value;
}

echo json_encode($res);