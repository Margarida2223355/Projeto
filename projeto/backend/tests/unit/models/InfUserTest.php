<?php

namespace backend\tests\unit\models;

use common\models\InfUser;
use common\models\User;
use Yii;

class InfUserTest extends \Codeception\Test\Unit
{
    public function testGetTotalUsers()
    {
        // Obter o número atual de usuários na base de dados
        $totalUsersNoBanco = InfUser::getTotalUsers();

        // Criar um usuário de teste
        $user = new User([
            'username' => 'testuser',
            'email' => 'testuser@example.com',
            'password_hash' => 'testpassword',
            'status' => 10, // Ativo
        ]);
        $user->save(false);

        // Criar uma informação de usuário associada ao usuário
        $infUser = new InfUser([
            'nome_completo' => 'Test User',
            'morada' => 'Test ender',
            'pais' => 'Test pais',
            'telefone' => '123456789',
            'salario' => 100,
            'nif' => '123456789',
            'id' => $user->id,
        ]);
        $infUser->save(false);

        // Chamar o método para obter o total de usuários
        $totalUsers = InfUser::getTotalUsers();

        // Deve retornar o número total de usuários criados
        $this->assertEquals($totalUsersNoBanco + 1, $totalUsers);

        // Limpar dados de teste
        $infUser->delete();
        $user->delete();
    }
    public function testGetStatus()
    {
        // Criar um utilizador ativo de teste
        $userAtivo = new User([
            'username' => 'userativo',
            'email' => 'userativo@example.com',
            'password_hash' => 'testpassword',
            'status' => 10, // Ativo
        ]);
        $userAtivo->save(false);

        $infUserAtivo = new InfUser([
            'nome_completo' => 'User Ativo',
            'morada' => 'Test ender',
            'pais' => 'Test pais',
            'telefone' => '123456789',
            'salario' => 100,
            'nif' => '123456789',
            'id' => $userAtivo->id,
        ]);
        $infUserAtivo->save(false);

        // Verificar se o status é 'ativo' para o usuário ativo
        $this->assertEquals('ativo', $infUserAtivo->getStatus());

        // Criar um utilizador inativo de teste
        $userInativo = new User([
            'username' => 'userinativo',
            'email' => 'userinativo@example.com',
            'password_hash' => 'testpassword',
            'status' => 9, // Inativo
        ]);
        $userInativo->save(false);

        $infUserInativo = new InfUser([
            'nome_completo' => 'User Inativo',
            'morada' => 'Test ender',
            'pais' => 'Test pais',
            'telefone' => '123456789',
            'salario' => 100,
            'nif' => '123456789',
            'id' => $userInativo->id,
        ]);
        $infUserInativo->save(false);

        // Verificar se o status é 'inativo' para o usuário inativo
        $this->assertEquals('inativo', $infUserInativo->getStatus());

        // Limpar dados de teste
        $infUserAtivo->delete();
        $userAtivo->delete();
        $infUserInativo->delete();
        $userInativo->delete();
    }
}
