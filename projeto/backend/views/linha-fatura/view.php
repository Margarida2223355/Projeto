<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\LinhaFatura $model */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Linha Faturas', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="linha-fatura-view">

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
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'quantidade',
            'descricao',
            [
                'attribute' => 'Serviço',
                'value' => function ($model) {
                    return $model->servico ? $model->servico->nome : null;;
                },
            ],
            [
                'attribute' => 'Refeição',
                'value' => function ($model) {
                    return $model->refeicao ? $model->refeicao->nome : null;;
                },
            ],
            'sub_total',
            'preco_unitario',
            'reserva_id',
            'status',
        ],
    ]) ?>

</div>
