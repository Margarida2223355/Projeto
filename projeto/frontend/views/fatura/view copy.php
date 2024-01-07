<?php

use yii\grid\GridView;
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
        ],
    ]) ?>

    <?= DetailView::widget([
        'model' => $model->reserva,
        'attributes' => [
            [
                'attribute' => 'id',
                'label' => 'Numero da Reserva',
            ],
            'quarto_id',
            [
                'attribute' => 'cliente.nome_completo',
                'label' => 'Cliente',
            ],
            'data_inicial',
            'data_final',
            [
                'attribute' => 'preco_total',
                'label' => 'Preço Total Diarias',
            ],
            
        ],
    ]); ?>

    <h2>LinhaFaturas</h2>
    
    <?= GridView::widget([
        'dataProvider' => new \yii\data\ArrayDataProvider([
            'allModels' => $model->reserva->linhaFaturas,
        ]),
        'columns' => [
            'id',
            'quantidade',
            'servico_id',
            'refeicao_id',
            'preco_unitario',
            'sub_total',
        ],
    ]); ?>

</div>
