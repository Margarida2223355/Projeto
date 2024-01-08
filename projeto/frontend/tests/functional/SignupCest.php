<?php

namespace frontend\tests\functional;

use frontend\tests\FunctionalTester;

class SignupCest
{
    protected $formId = '#form-signup';


    public function _before(FunctionalTester $I)
    {
        $I->amOnRoute('site/signup');
    }

    public function signupWithEmptyFields(FunctionalTester $I)
    {
        $I->see('Signup', 'h1');
        $I->see('Please fill out the following fields to signup:');
        $I->submitForm($this->formId, []);
        $I->seeValidationError('Username cannot be blank.');
        $I->seeValidationError('Email cannot be blank.');
        $I->seeValidationError('Password cannot be blank.');
        $I->seeValidationError('Nome Completo cannot be blank.');
        $I->seeValidationError('Morada cannot be blank.');
        $I->seeValidationError('Pais cannot be blank.');
        $I->seeValidationError('Telefone cannot be blank.');
        $I->seeValidationError('Nif cannot be blank.');

    }

    public function signupWithWrongEmail(FunctionalTester $I)
    {
        $I->submitForm(
            $this->formId, [
            'SignupForm[username]'  => 'tester',
            'SignupForm[email]'     => 'ttttt',
            'SignupForm[password]'  => 'tester_password',
            'SignupForm[nome_completo]'  => 'tester tester',
            'SignupForm[morada]'  => 'rua tester',
            'SignupForm[pais]'  => 'pais tester',
            'SignupForm[telefone]'  => '66666666',
            'SignupForm[nif]'  => '999999666',
        ]
        );
        $I->dontSee('Username cannot be blank.', '.invalid-feedback');
        $I->dontSee('Password cannot be blank.', '.invalid-feedback');
        $I->see('Email is not a valid email address.', '.invalid-feedback');

    }

    public function signupSuccessfully(FunctionalTester $I)
    {
        $I->submitForm($this->formId, [
            'SignupForm[username]' => 'tester',
            'SignupForm[email]' => 'tester.email@example.com',
            'SignupForm[password]' => 'tester_password',
            'SignupForm[nome_completo]'  => 'tester tester',
            'SignupForm[morada]'  => 'rua tester',
            'SignupForm[pais]'  => 'pais tester',
            'SignupForm[telefone]'  => '66666666',
            'SignupForm[nif]'  => '999999666',
        ]);

        $I->seeRecord('common\models\User', [
            'username' => 'tester',
            'email' => 'tester.email@example.com',
            'status' => \common\models\User::STATUS_INACTIVE
        ]);
        $I->seeRecord('common\models\infUser', [
            'nome_completo' => 'tester tester',
            'morada' => 'rua tester',
            'pais' => 'pais tester',
            'telefone' => '66666666',
            'nif' => '999999666',
        ]);
        $I->seeEmailIsSent();
        $I->see('Thank you for registration. Please check your inbox for verification email.');
    }
}
