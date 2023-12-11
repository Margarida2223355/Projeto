<?php

use common\models\LinhaFatura;
use yii\helpers\Html;
use yii\widgets\DetailView;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var common\models\Reserva $model */

$this->title = "Reserva ".$model->id;
$this->params['breadcrumbs'][] = ['label' => 'Reservas', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="reserva-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], ['class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'quarto_id',
            [
                'attribute' => 'cliente.nome_completo',
                'label' => 'Cliente',
            ],
            'data_inicial',
            'data_final',
            'preco_total',
            'status',
            [
                'attribute' => 'linhaFaturas',
                'label' => 'Subtotal',
                'value' => function ($model) {
                    $subTotals = array_map(function ($linhaFatura) {
                        return $linhaFatura->sub_total;
                    }, $model->linhaFaturas);
                    return implode(', ', $subTotals);
                },
            ],
        ],
    ]); ?>

    <h2>LinhaFaturas</h2>

    <?= GridView::widget([
        'dataProvider' => new \yii\data\ArrayDataProvider([
            'allModels' => $model->linhaFaturas,
        ]),
        'columns' => [
            'id',
            'quantidade',
            'servico_id',
            'refeicao_id',
            'preco_unitario',
            'sub_total',
            'status',
        ],
    ]); ?>

</div>
