<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\DescricaoCusto $model */

$this->title = 'Create Descricao Custo';
$this->params['breadcrumbs'][] = ['label' => 'Descricao Custos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="descricao-custo-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
