<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "fatura".
 *
 * @property int $id
 * @property int $pousada_id
 * @property string|null $data_pagamento
 * @property int $reserva_id
 * @property float|null $preco_total
 * @property float|null $total_sem_imposto
 *
 * @property Pousada $pousada
 * @property Reserva $reserva
 */
class Fatura extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'fatura';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['pousada_id', 'reserva_id'], 'required'],
            [['pousada_id', 'reserva_id'], 'integer'],
            [['data_pagamento'], 'safe'],
            [['preco_total', 'total_sem_imposto'], 'number'],
            [['reserva_id'], 'exist', 'skipOnError' => true, 'targetClass' => Reserva::class, 'targetAttribute' => ['reserva_id' => 'id']],
            [['pousada_id'], 'exist', 'skipOnError' => true, 'targetClass' => Pousada::class, 'targetAttribute' => ['pousada_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'pousada_id' => 'Pousada ID',
            'data_pagamento' => 'Data Pagamento',
            'reserva_id' => 'Reserva ID',
            'preco_total' => 'Preco Total',
            'total_sem_imposto' => 'Total Sem Imposto',
        ];
    }

    /**
     * Gets query for [[Pousada]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getPousada()
    {
        return $this->hasOne(Pousada::class, ['id' => 'pousada_id']);
    }

    /**
     * Gets query for [[Reserva]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReserva()
    {
        return $this->hasOne(Reserva::class, ['id' => 'reserva_id']);
    }
}
