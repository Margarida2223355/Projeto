<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\Fatura $model */

$this->title = $model->id;
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);

$script = <<< JS
    function printPage() {
        window.print();
    }
JS;

$this->registerJs($script, yii\web\View::POS_HEAD);
?>
<div class="fatura-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
        <?= Html::button('Print', ['class' => 'btn btn-info', 'onclick' => 'printPage()']) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'data_pagamento',
            [
                'attribute' => 'Pousada',
                'value' => function ($model) {
                    return $model->pousada->denominacao_social;
                },
            ],
            [
                'attribute' => 'NIF',
                'value' => function ($model) {
                    return $model->pousada->nif;
                },
            ],
            [
                'attribute' => 'Morada Empresa',
                'value' => function ($model) {
                    return $model->pousada->morada;
                },
            ],
            'reserva_id',
            'preco_total',
            'total_sem_imposto',
        ],
    ]) ?>

</div>
