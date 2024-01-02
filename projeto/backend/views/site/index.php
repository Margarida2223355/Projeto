<?php
$this->title = 'Dashboard';
$this->params['breadcrumbs'] = [['label' => $this->title]];
?>
<div class="container-fluid">
        <div class="row">

            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\InfoBox::widget([
                    'id' => 'total-voos-info-box',
                    'text' => 'Utilizadores Cadastrados',
                    'number' => $numeroUsers,
                    'theme' => 'info',
                    'icon' => 'fas fa-plane-departure',
                ]);


                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\InfoBox::widget([
                    'id' => 'voos-hoje-info-box',
                    'text' => 'Total Reservas',
                    'number' => $totalReservas,
                    'theme' => 'success',
                    'icon' => 'fas fa-plane-departure',
                ]);
            ?>
            </div>
        </div>
        <div class="row">

            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'avioes-inoperacionais-info-box',
                    'text' => 'Total de Quartos',
                    'title' => $numeroQuartos,
                    'theme' => 'danger',
                    'icon' => 'fas fa-plane',
                    'linkUrl' => '../aviao/index'
                ]);
                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'pistas-info-box',
                    'text' => 'Total Refeições',
                    'title' => $numeroRefeicoes,
                    'theme' => 'danger',
                    'icon' => 'fas fa-road',
                    'linkUrl' => '../pista/index'
                ]);
                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'avioes-danificados-info-box',
                    'text' => 'Total Servicos',
                    'title' => $numeroServicos,
                    'theme' => 'danger',
                    'icon' => 'fas fa-plane',
                    'linkUrl' => '../aviao/index'
                ]);
                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'avioes-manutencao-info-box',
                    'text' => 'Avioes em manutenção',
                    'title' => $numeroUsers,
                    'theme' => 'warning',
                    'icon' => 'fas fa-plane',
                    'linkUrl' => '../aviao/index'
                ]);
                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'voos-planeados-info-box',
                    'text' => 'Voos planeados',
                    'title' => $numeroUsers,
                    'theme' => 'info',
                    'icon' => 'fas fa-plane-departure',
                    'linkUrl' => '../voo/index'
                ]);
                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'voos-circulacao-info-box',
                    'text' => 'Voos em circulacao',
                    'title' => $numeroUsers,
                    'theme' => 'success',
                    'icon' => 'fas fa-plane-departure',
                    'linkUrl' => '../voo/index'
                ]);
                ?>
            </div>
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'voos-atrasados-info-box',
                    'text' => 'Voos atrasados',
                    'title' => $numeroUsers,
                    'theme' => 'danger',
                    'icon' => 'fas fa-plane-slash',
                    'linkUrl' => '../voo/index'
                ]);
                ?>
            </div>
        </div>

    <?php
    if(Yii::$app->user->can('gestor')){
        ?>
        <div class="row">
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'funcionarios-info-box',
                    'text' => 'Total de funcionarios',
                    'title' => $numeroUsers,
                    'theme' => 'success',
                    'icon' => 'fas fa-users',
                    'linkUrl' => '../funcionario/index'
                ]);
                ?>
            </div>
        </div>
    <?php }
    ?>

    <?php
    if(Yii::$app->user->can('funcionario')){
        ?>
        <div class="row">
            <div class="col-lg-3 col-6">
                <?=
                \hail812\adminlte\widgets\SmallBox::widget([
                    'id' => 'recurso-info-box',
                    'text' => 'Total de recursos inferiores a 100 unidades',
                    'title' => $numeroUsers,
                    'theme' => 'warning',
                    'icon' => 'fas fa-box',
                    'linkUrl' => '../recurso/index'
                ]);
                ?>
            </div>
        </div>
    <?php }
    ?>


</div>