<?php

use common\models\LinhaFatura;
use yii\helpers\Html;
use yii\grid\ActionColumn;
use yii\grid\GridView;
use yii\web\View;

/** @var View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Linha Faturas';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="linha-fatura-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>        
        <?= Html::button('Confirmados', ['class' => 'btn btn-info', 'id' => 'btn-confirmados']) ?>
        <?= Html::button('Carrinhos', ['class' => 'btn btn-info', 'id' => 'btn-carrinhos']) ?>
        <?= Html::button('Cancelados', ['class' => 'btn btn-info', 'id' => 'btn-cancelados']) ?>
        <?= Html::button('Todos', ['class' => 'btn btn-primary', 'id' => 'btn-todos']) ?>
    </p>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'id' => 'grid-view',
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],
            'id',
            'reserva_id',
            'quantidade',
            'servico_id',
            'refeicao_id',
            'status',
            [
                'class' => ActionColumn::className(),
            ],
            [
                'class' => 'yii\grid\ActionColumn',
                'template' => '{confirmar}',
                'buttons' => [
                    'confirmar' => function ($url, $model, $key) {
                        return Html::a('Confirmar', ['confirmar', 'id' => $model->id], [
                            'class' => 'btn btn-success',
                            'data-method' => 'post',
                        ]);
                    },
                ],
            ],
        ],
    ]); ?>

</div>

<?php
$script = <<< JS
    // Botoes para mostrar e esconder de acordo com o status
    $(document).ready(function () {
        $('#btn-confirmados').click(function () {
            filterRowsByStatus('Confirmado');
        });
        $('#btn-carrinhos').click(function () {
            filterRowsByStatus('Carrinho');
        });
        $('#btn-cancelados').click(function () {
            filterRowsByStatus('Cancelado');
        });
        $('#btn-todos').click(function () {
            filterRowsByStatus('');
        });

        function filterRowsByStatus(status) {
            // Esconde todos
            $('#grid-view tbody tr').hide();

            // Mostra apenas as linhas com o status especificado
            $('#grid-view tbody tr td:nth-last-child(3):contains(' + status + ')').parent().show();
        }
    });
JS;

$this->registerJs($script);
?>


