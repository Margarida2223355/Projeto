<?php

use common\models\LinhaFatura;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */

$reserva_id = Yii::$app->request->get('reserva_id');
$this->title = 'Linhas Fatura Reserva ' . $reserva_id;
$this->params['breadcrumbs'][] = ['label' => 'Reserva', 'url' => ['reserva/view','id' => $reserva_id]];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="linha-fatura-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Refeições', ['create', 'reserva_id' => $reserva_id,'tipo' => 'refeicao'], ['class' => 'btn btn-success']) ?>
        <?= Html::a('Serviços', ['create', 'reserva_id' => $reserva_id,'tipo' => 'servico'], ['class' => 'btn btn-success']) ?>
    </p>


    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'quantidade',
            'servico_id',
            'refeicao_id',
            'sub_total',
            //'preco_unitario',
            //'reserva_id',
            //'status',
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, LinhaFatura $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); ?>


</div>
