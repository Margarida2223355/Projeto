<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\InfCliente $model */

$this->title = 'Update Inf Cliente: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Inf Clientes', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="inf-cliente-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
