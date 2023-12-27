<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "refeicao".
 *
 * @property int $id
 * @property string $nome
 * @property float $preco
 * @property string $data
 * @property string $horario
 *
 * @property LinhaFatura[] $linhaFaturas
 */
class Refeicao extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'refeicao';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['nome', 'preco', 'data', 'horario'], 'required'],
            [['preco'], 'number'],
            [['data'], 'safe'],
            [['horario'], 'string'],
            [['nome'], 'string', 'max' => 50],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nome' => 'Nome',
            'preco' => 'Preco',
            'data' => 'Data',
            'horario' => 'Horario',
        ];
    }

    /**
     * Gets query for [[LinhaFaturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFaturas()
    {
        return $this->hasMany(LinhaFatura::class, ['refeicao_id' => 'id']);
    }
}
