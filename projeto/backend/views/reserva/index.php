<?php

use common\models\Reserva;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;
use hail812\adminlte\widgets\Alert;

/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Reservas';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="reserva-index">

    <h1><?= Html::encode($this->title) ?></h1>
    
    <?php 
        if (Yii::$app->session->hasFlash('error')) {
            echo Alert::widget([
                'type' => 'danger',
                'body' => Yii::$app->session->getFlash('error'),
            ]);
        } 
    ?>
    
    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],
            [
                'attribute' => 'id',
                'label' => 'Reserva',
            ],
            'quarto_id',
            'cliente_id',
            'data_inicial',
            'data_final',
            //'preco_total',
            'status',

            [
                'class' => ActionColumn::className(),
                'template' => '{view} {update} {delete} {customButton}',
                'buttons' => [
                    'customButton' => function ($url, $model, $key) {
                        return Html::a('<i class="fas fa-plus"></i>', ['linha-fatura/index', 'reserva_id' => $model->id]);
                    },
                ],
            ],
        ],
    ]); ?>


</div>
