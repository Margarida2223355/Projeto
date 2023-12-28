<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Pousada $model */

$this->title = 'Update Pousada: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Pousadas', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="pousada-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
