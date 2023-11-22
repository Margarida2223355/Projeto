<?php

use common\models\infUser;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */


$this->title = 'Inf Users';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="inf-user-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Inf User', ['create'], ['class' => 'btn btn-success']) ?>
    </p>

    <?php if(Yii::$app->user->can('crudCliente') && !Yii::$app->user->can('crudFuncionario') ){?>
    <?=
        GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'nome_completo',
            'morada',
            'pais',
            //'telefone',
            //'salario',
            //'nif',
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, infUser $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
        'rowOptions' => function ($model) {
            return ['style' => $model->getRole() !== 'cliente' ? 'display:none;' : ''];
        },
    ]); }?>

    <?php if(Yii::$app->user->can('crudFuncionario')){?>
    <?=
        GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'nome_completo',
            'morada',
            'pais',
            'role',
            'status',
            [
                'header' => 'Ações',
                'class' => 'yii\grid\ActionColumn',
                'template' => '{setStatus}',
                'buttons' => [
                    'setStatus' => function ($url, $model, $key) {
                        return Html::a(
                            'Set Status',
                            ['set-status', 'id' => $model->id],
                            [
                                'class' => 'btn btn-success',
                                'data' => [
                                    //'confirm' => 'Tem certeza de que deseja definir o status?',
                                    'method' => 'post',
                                ],
                            ]
                        );
                    },
                ],
            ],
            //'telefone',
            //'salario',
            //'nif',
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, infUser $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); }?>


</div>
