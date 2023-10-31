<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\InfCliente $model */

$this->title = 'Create Inf Cliente';
$this->params['breadcrumbs'][] = ['label' => 'Inf Clientes', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="inf-cliente-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
