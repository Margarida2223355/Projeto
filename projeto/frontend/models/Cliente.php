<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "cliente".
 *
 * @property int $id
 * @property string $nome_completo
 * @property string $telefone
 * @property string $morada
 * @property string $pais
 * @property string $nif
 */
class Cliente extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'cliente';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['nome_completo', 'telefone', 'morada', 'pais', 'nif'], 'required'],
            [['nome_completo', 'morada'], 'string', 'max' => 100],
            [['telefone'], 'string', 'max' => 20],
            [['pais'], 'string', 'max' => 50],
            [['nif'], 'string', 'max' => 15],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nome_completo' => 'Nome Completo',
            'telefone' => 'Telefone',
            'morada' => 'Morada',
            'pais' => 'Pais',
            'nif' => 'Nif',
        ];
    }
}
