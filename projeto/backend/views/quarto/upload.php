<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Quarto $model */

$this->title = 'Upload';
$this->params['breadcrumbs'][] = ['label' => 'Quartos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="upload">

    <h1><?= Html::encode($this->title) ?></h1>
    
    <?php $form = ActiveForm::begin(['options'=> ['enctype'=>'multipart/form-data']]) ?>
    
        <?= $form->field($upload,'image[]')->fileInput(['multiple'=>true])?>
        <?= $form->field($upload,'quarto_id')->dropDownList($quartos)?>
        <?= $form->field($upload,'descricao')->input('text')?>

        <?= Html::submitButton('Upload',['class'=>'btn btn-primary'])?>

    <?php ActiveForm::end() ?>

</div>
