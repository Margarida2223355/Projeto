<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Reserva $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="reserva-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'quarto_id')->textInput() ?>

    <?= $form->field($model, 'cliente_id')->textInput() ?>

    <?= $form->field($model, 'data_inicial')->textInput() ?>

    <?= $form->field($model, 'data_final')->textInput() ?>

    <?= $form->field($model, 'preco_total')->textInput() ?>

    <?= $form->field($model, 'status')->dropDownList([ 'carrinho' => 'Carrinho', 'pago' => 'Pago', 'cancelada' => 'Cancelada', ], ['prompt' => '']) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
