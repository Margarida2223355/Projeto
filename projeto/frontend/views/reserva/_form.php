<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use yii\jui\DatePicker;


/** @var yii\web\View $this */
/** @var common\models\Reserva $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="reserva-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'quarto_id')->textInput() ?>

    <?= $form->field($model, 'cliente_id')->textInput() ?>

    <?= $form->field($model, 'data_inicial')->widget(DatePicker::class, [
        'language' => 'pt',
        'dateFormat' => 'yyyy-MM-dd',
        'options' => ['class' => 'form-control'],
        'clientOptions' => [
            'minDate' => 0, // Data de hoje
            'maxDate' => '+365d', // Máximo de 365 dias a partir de hoje
        ]
    ]) ?>
    <!-- Quando o user seleciona uma data_inicial o minDate da data_final altera para a data selecionada no data_inicial -->
    <?php
        $this->registerJs("
        $('#" . Html::getInputId($model, 'data_inicial') . "').change(function() {
            var dataInicial = $(this).val();
            $('#" . Html::getInputId($model, 'data_final') . "').datepicker('option', 'minDate', dataInicial);
            $('#" . Html::getInputId($model, 'data_final') . "').datepicker('setDate', null);
        });
        ");
    ?>

    <?= $form->field($model, 'data_final')->widget(DatePicker::class, [
        'language' => 'pt',
        'dateFormat' => 'yyyy-MM-dd',
        'options' => ['class' => 'form-control'],
        'clientOptions' => [
            'minDate' => 0, // Data de hoje
            'maxDate' => '+365d', // Máximo de 365 dias a partir de hoje
        ]
    ]) ?>

    <?= $form->field($model, 'preco_total')->textInput() ?>

    <?= $form->field($model, 'status')->dropDownList([ 'carrinho' => 'Carrinho', 'pago' => 'Pago', 'cancelada' => 'Cancelada', ], ['prompt' => '']) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
