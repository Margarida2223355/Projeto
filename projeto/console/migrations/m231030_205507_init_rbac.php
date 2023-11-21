<?php

use yii\db\Migration;

/**
 * Class m231030_205507_init_rbac
 */
class m231030_205507_init_rbac extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        // *****ATENÇÃO PRECISO SEPARAR O READ DO CRUD PARA O CLIENTE*****
        
        $auth = Yii::$app->authManager;
        //Criar permissões
        $permission_aceder_backend = $auth->createPermission('acederBackend');
        $auth->add($permission_aceder_backend);

        $permission_crud_cliente = $auth->createPermission('crudCliente');
        $auth->add($permission_crud_cliente);
        
        $permission_self = $auth->createPermission('permission_self_operations');
        $auth->add($permission_self);

        $permission_crud_funcionario = $auth->createPermission('crudFuncionario');
        $auth->add($permission_crud_funcionario);

        $permission_crud_quarto = $auth->createPermission('crudQuarto');
        $auth->add($permission_crud_quarto);

        $permission_crud_reserva = $auth->createPermission('crudReserva');
        $auth->add($permission_crud_reserva);

        $permission_crud_custo = $auth->createPermission('crudCusto');
        $auth->add($permission_crud_custo);

        $permission_crud_servico = $auth->createPermission('crudServico');
        $auth->add($permission_crud_servico);

        $permission_crud_tarefa = $auth->createPermission('crudTarefa');
        $auth->add($permission_crud_tarefa);

        $permission_crud_refeicao = $auth->createPermission('crudRefeicao');
        $auth->add($permission_crud_refeicao);


        //Criar role e atribuir permissões
        $role_cliente = $auth->createRole('cliente');
        $auth->add($role_cliente);
        $auth->addChild($role_cliente, $permission_crud_reserva);
        $auth->addChild($role_cliente, $permission_self);

        $role_funcionario = $auth->createRole('funcionario');
        $auth->add($role_funcionario);
        $auth->addChild($role_funcionario, $permission_crud_cliente);
        $auth->addChild($role_funcionario, $permission_crud_servico);
        $auth->addChild($role_funcionario, $permission_crud_refeicao);
        $auth->addChild($role_funcionario, $permission_aceder_backend);
        $auth->addChild($role_funcionario, $role_cliente);

        $role_gestor = $auth->createRole('gestor');
        $auth->add($role_gestor);
        $auth->addChild($role_gestor, $permission_crud_funcionario);
        $auth->addChild($role_gestor, $permission_crud_custo);
        $auth->addChild($role_gestor, $permission_crud_quarto);
        $auth->addChild($role_gestor, $permission_crud_tarefa);
        $auth->addChild($role_gestor, $role_funcionario);

        $auth->assign($role_gestor, 1);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $auth = Yii::$app->authManager;

        $auth->removeAll();
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m231030_205507_init_rbac cannot be reverted.\n";

        return false;
    }
    */
}
