<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Fatura $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="fatura-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'data_pagamento')->textInput() ?>

    <?= $form->field($model, 'denominacao_social')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'morada_empresa')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'nif')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'reserva_id')->textInput() ?>

    <?= $form->field($model, 'preco_total')->textInput() ?>

    <?= $form->field($model, 'total_sem_imposto')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
