<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Refeicao $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="refeicao-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'nome')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'preco')->textInput() ?>
    
    <?= $form->field($model, 'data')->textInput(['type' => 'date']) ?>

    <?= $form->field($model, 'categoria')->dropDownList(['Almoco' => 'Almoço', 'Jantar' => 'Jantar']) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
