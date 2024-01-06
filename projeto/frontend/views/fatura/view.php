<?php

use yii\grid\GridView;
use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\Fatura $model */


\yii\web\YiiAsset::register($this);

?>

<div class="invoice p-3 mb-3">

<div class="row">
        <div class="col-12">
            <h4>
                <i class="fas fa-globe"></i> <?= Html::encode($model->pousada->denominacao_social) ?>
                <small class="float-right">Date: <?= Yii::$app->formatter->asDate($model->data_pagamento, 'php:m/d/Y') ?></small>
            </h4>
        </div>
    </div>

    <div class="row invoice-info">
        <div class="col-sm-4 invoice-col">
            From
            <address>
                <strong><?= Html::encode($model->pousada->denominacao_social) ?></strong><br>
                Morada: <?= Html::encode($model->pousada->morada) ?><br>
                NIF: <?= Html::encode($model->pousada->nif) ?><br>
            </address>
        </div>

        <div class="col-sm-4 invoice-col">
            To
            <address>
                <strong><?= Html::encode($model->reserva->cliente->nome_completo) ?></strong><br>
                NIF: <?= Html::encode($model->reserva->cliente->nif) ?><br>
                Morada: <?= Html::encode($model->reserva->cliente->morada) ?><br>
                Phone: <?= Html::encode($model->reserva->cliente->telefone) ?><br>
                Email: <?= Html::encode($model->reserva->cliente->email) ?><br>

            </address>
        </div>

        <div class="col-sm-4 invoice-col">
            <b>Invoice #<?= Html::encode($model->id) ?></b><br>
            <br>
            <b>Order ID:</b> <?= Html::encode($model->reserva->id) ?><br>
            <b>Payment Due:</b> <?= Yii::$app->formatter->asDate($model->data_pagamento, 'php:d/m/Y') ?><br>
            <b>Account:</b> <?= Html::encode($model->pousada->nif) ?>
        </div>
    </div>
    <div class="row">
        <div class="col-12 table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Qty</th>
                        <th>Product</th>
                        <th>Description</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
        <!-- First row with reserva information -->
        <tr>
            <td>1</td>
            <td>Quarto <?= Html::encode($model->reserva->quarto_id) ?></td>
            <td>Estadia entre <?= Yii::$app->formatter->asDate($model->reserva->data_inicial, 'php:d/m/Y') ?> e <?= Yii::$app->formatter->asDate($model->reserva->data_final, 'php:m/d/Y')?></td>
            <td><?= Yii::$app->formatter->asCurrency($model->reserva->preco_total) ?></td>
        </tr>

        <!-- Rows for linhaFaturas -->
        <?php foreach ($model->reserva->linhaFaturas as $linhaFatura): ?>
            <tr>
                <td><?= Html::encode($linhaFatura->quantidade) ?></td>
                <td><?php
                    $productName = '';
                    if ($linhaFatura->servico !== null) {
                        $productName = $linhaFatura->servico->nome;
                    } elseif ($linhaFatura->refeicao !== null) {
                        $productName = $linhaFatura->refeicao->nome;
                    }
                    echo Html::encode($productName);
                ?></td>
                <td>-</td>
                <td><?= Yii::$app->formatter->asCurrency($linhaFatura->sub_total) ?></td>
            </tr>
        <?php endforeach; ?>
    </tbody>
            </table>
        </div>
        <div class="col-6 offset-md-6">
            <p class="lead">Amount Due <?= Yii::$app->formatter->asDate($model->data_pagamento, 'php:d/m/Y') ?></p>
            <div class="table-responsive">
                <table class="table">
                    <tbody>
                    <tr>
                        <th>Total:</th>
                        <td><?= Yii::$app->formatter->asCurrency($model->preco_total) ?></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>