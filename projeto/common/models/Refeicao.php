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
 * @property string $categoria
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
            [['nome', 'preco', 'data', 'categoria'], 'required'],
            [['preco'], 'number'],
            [['data'], 'safe'],
            [['categoria'], 'string'],
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
            'categoria' => 'Categoria',
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
    public static function getTotalRefeicoes(){
        return static::find()->count();
    }
}
