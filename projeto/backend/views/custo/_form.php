<?php

use yii\helpers\Html;
use yii\jui\DatePicker;
use yii\widgets\ActiveForm;
use backend\models\DescricaoCusto;
use yii\helpers\ArrayHelper;

/** @var yii\web\View $this */
/** @var app\models\Custo $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="custo-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'descricao_custo_id')->dropDownList(
    ArrayHelper::map(DescricaoCusto::find()->all(), 'id', 'descricao'),
    ['prompt' => 'Selecione uma descrição de custo']
) ?>
    <?= $form->field($model, 'valor')->textInput() ?>

    <?= $form->field($model, 'data')->widget(DatePicker::class, [
        'language' => 'pt',
        'dateFormat' => 'yyyy-MM-dd',
        'options' => ['class' => 'form-control'],

]) ?>
    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
