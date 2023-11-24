<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Refeicao $model */

$this->title = 'Create Refeicao';
$this->params['breadcrumbs'][] = ['label' => 'Refeicaos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="refeicao-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
