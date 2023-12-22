<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "imposto".
 *
 * @property int $id
 * @property float $valor
 * @property string $descricao
 *
 * @property Refeicao[] $refeicaos
 * @property Reserva[] $reservas
 * @property Servico[] $servicos
 */
class Imposto extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'imposto';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['valor', 'descricao'], 'required'],
            [['valor'], 'number'],
            [['descricao'], 'string', 'max' => 250],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'valor' => 'Valor',
            'descricao' => 'Descricao',
        ];
    }

    /**
     * Gets query for [[Refeicaos]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getRefeicaos()
    {
        return $this->hasMany(Refeicao::class, ['imposto_id' => 'id']);
    }

    /**
     * Gets query for [[Reservas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReservas()
    {
        return $this->hasMany(Reserva::class, ['imposto_id' => 'id']);
    }

    /**
     * Gets query for [[Servicos]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getServicos()
    {
        return $this->hasMany(Servico::class, ['imposto_id' => 'id']);
    }
}
