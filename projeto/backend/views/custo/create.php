<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\Custo $model */

$this->title = 'Create Custo';
$this->params['breadcrumbs'][] = ['label' => 'Custos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="custo-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
