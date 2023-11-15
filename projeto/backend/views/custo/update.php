<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\Custo $model */

$this->title = 'Update Custo: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Custos', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="custo-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
