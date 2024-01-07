<?php
$this->title = 'Dashboard';
$this->params['breadcrumbs'] = [['label' => $this->title]];
?>
<div class="container-fluid">

    <?php
    if(Yii::$app->user->can('funcionario')){
        ?>
        <div class="row">

        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\InfoBox::widget([
                'id' => 'reserva-semana-info-box',
                'text' => 'Reservas da Semana',
                'number' => $reservasSemana,
                'theme' => 'success',
                'icon' => 'fas fa-book',
            ]);
        ?>
        </div>
        </div>
        <div class="row">

        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\SmallBox::widget([
                'id' => 'total-quartos-info-box',
                'text' => 'Total de Quartos',
                'title' => $numeroQuartos,
                'theme' => 'info',
                'icon' => 'fas fa-bed',
                'linkUrl' => '../quarto/index'
            ]);
            ?>
        </div>
        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\SmallBox::widget([
                'id' => 'numero-refeicoes-info-box',
                'text' => 'Total Refeições',
                'title' => $numeroRefeicoes,
                'theme' => 'info',
                'icon' => 'fas fa-utensils',
                'linkUrl' => '../refeicao/index'
            ]);
            ?>
        </div>
        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\SmallBox::widget([
                'id' => 'total-servicos-info-box',
                'text' => 'Total Servicos',
                'title' => $numeroServicos,
                'theme' => 'info',
                'icon' => 'fas fa-hiking',
                'linkUrl' => '../servico/index'
            ]);
            ?>
        </div>
        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\SmallBox::widget([
                'id' => 'utilizadores-cadastrados-info-box',
                'text' => 'Utilizadores Cadastrados',
                'title' => $numeroUsers,
                'theme' => 'info',
                'icon' => 'fas fa-users',
                'linkUrl' => '../inf-user/index'
            ]);
            ?>
        </div>
        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\SmallBox::widget([
                'id' => 'total-reservas-info-box',
                'text' => 'Total Reservas',
                'title' => $totalReservas,
                'theme' => 'info',
                'icon' => 'fas fa-book',
                'linkUrl' => '../reserva/index'
            ]);
            ?>
        </div>
        <div class="col-lg-3 col-6">
            <?=
            \hail812\adminlte\widgets\SmallBox::widget([
                'id' => 'numero-faturas-info-box',
                'text' => 'Total de Faturas',
                'title' => $numeroFaturas,
                'theme' => 'info',
                'icon' => 'fas fa-file-invoice',
                'linkUrl' => '../fatura/index'
            ]);
            ?>
        </div>

        </div>
        </div>
    <?php }
    ?>
<?php
    if(Yii::$app->user->can('gestor')){
        ?>
        <div class="row">
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'gasto-atual-info-box',
                    'text' => 'Gasto Mes Atual',
                    'title' => $totalMesAtual,
                    'theme' => 'success',
                    'icon' => 'fas fa-users',
                    'linkUrl' => '../custo/index'
                ]);
                ?>
            </div>
            
        </div>
    <?php }
    ?>

</div>