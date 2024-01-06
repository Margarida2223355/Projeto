<?php

use common\models\Quarto;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Quartos';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="quarto-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Quarto', ['create'], ['class' => 'btn btn-success']) ?>
        <?= Html::a('Upload Image', ['upload'], ['class' => 'btn btn-primary']) ?>
    </p>


    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'descricao',
            'camas_solteiro',
            'camas_casal',
            'arcondicionado',
            //'aquecedor',
            //'preco',
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, Quarto $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); ?>


</div>
