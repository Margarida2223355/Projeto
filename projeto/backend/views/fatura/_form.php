<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Fatura $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="fatura-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'data_pagamento')->textInput(['readonly' => true]) ?>
   
    <?= $form->field($model, 'pousada_id')->textInput(['readonly' => true]) ?>
    
    <?= $form->field($model, 'reserva_id')->textInput(['readonly' => true]) ?>

    <?= $form->field($model, 'preco_total')->textInput(['readonly' => true]) ?>


    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
