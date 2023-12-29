<?php

use yii\helpers\Html;
use yii\jui\DatePicker;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Reserva $model */

$this->title = 'Update Reserva: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Reservas', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="reserva-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?php $form = ActiveForm::begin(); ?>
    
    <?= $form->field($model, 'quarto_id')->input($model->quarto_id)?>

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

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>
</div>
