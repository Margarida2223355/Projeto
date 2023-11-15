<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\DescricaoCusto $model */

$this->title = 'Update Descricao Custo: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Descricao Custos', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="descricao-custo-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
