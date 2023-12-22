<?php

use yii\jui\DatePicker;
use common\models\Reserva;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;
use yii\widgets\ActiveForm;


/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Reservas';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="reserva-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <?php $form = ActiveForm::begin(); ?>
    <p>
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
    <?= Html::a('Create Reserva', ['create'], [
        'class' => 'btn btn-success',
        'onclick' => '
            var dataInicial = $("#' . Html::getInputId($model, 'data_inicial') . '").val();
            var dataFinal = $("#' . Html::getInputId($model, 'data_final') . '").val();
            var url = "' . Yii::$app->urlManager->createUrl(['reserva/quartos-disponiveis']) . '&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal;
            window.location.href = url;
            return false;
        ',
    ]) ?>    </p>
    <?php ActiveForm::end(); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],
            [
                'attribute' => 'id',
                'label' => 'Reserva',
            ],
            'quarto_id',
            'data_inicial',
            'data_final',
            //'preco_total',
            //'status',
            // [
            //     'class' => ActionColumn::className(),
            //     'urlCreator' => function ($action, Reserva $model, $key, $index, $column) {
            //         return Url::toRoute([$action, 'id' => $model->id]);
            //     },
            // ],
            // [
            //     'header' => 'Adicionais',
            //     'format' => 'raw',
            //     'value' => function ($model) {
            //         return Html::a('<i class="fas fa-shopping-cart"></i>', ['linha-fatura/index', 'reserva_id' => $model->id]);
            //     },
            // ],
            [
                'class' => ActionColumn::className(),
                'template' => '{view} {update} {delete} {customButton}',
                'buttons' => [
                    'customButton' => function ($url, $model, $key) {
                        return Html::a('<i class="fas fa-plus"></i>', ['linha-fatura/index', 'reserva_id' => $model->id]);
                    },
                ],
            ],
        ],
    ]); ?>
</div>
