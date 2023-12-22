<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var backend\models\Imposto $model */

$this->title = 'Create Imposto';
$this->params['breadcrumbs'][] = ['label' => 'Impostos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="imposto-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
