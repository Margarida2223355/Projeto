<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use yii\jui\DatePicker;
use yii\helpers\ArrayHelper;

/** @var yii\web\View $this */
/** @var common\models\Reserva $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="reserva-form">

    <?php $form = ActiveForm::begin(); ?>
    
    <?= $form->field($model, 'quarto_id')->input($model->quarto_id, ['readonly' => true])?>

    <?= $form->field($model, 'data_inicial')->input([$model->data_inicial, 'readonly' => true])?>
    
    <?= $form->field($model, 'data_final')->input([$model->data_final, 'readonly' => true])?>

    <?= $form->field($model, 'preco_total')->textInput([ 'readonly' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>
    
</div>
