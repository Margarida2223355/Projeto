<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "fatura".
 *
 * @property int $id
 * @property string|null $data_pagamento
 * @property string $denominacao_social
 * @property string $morada_empresa
 * @property string $nif
 * @property int $reserva_id
 * @property float|null $preco_total
 * @property float|null $total_sem_imposto
 *
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
            [['data_pagamento'], 'safe'],
            [['denominacao_social', 'morada_empresa', 'nif', 'reserva_id'], 'required'],
            [['reserva_id'], 'integer'],
            [['preco_total', 'total_sem_imposto'], 'number'],
            [['denominacao_social', 'morada_empresa'], 'string', 'max' => 250],
            [['nif'], 'string', 'max' => 20],
            [['reserva_id'], 'exist', 'skipOnError' => true, 'targetClass' => Reserva::class, 'targetAttribute' => ['reserva_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'data_pagamento' => 'Data Pagamento',
            'denominacao_social' => 'Denominacao Social',
            'morada_empresa' => 'Morada Empresa',
            'nif' => 'Nif',
            'reserva_id' => 'Reserva ID',
            'preco_total' => 'Preco Total',
            'total_sem_imposto' => 'Total Sem Imposto',
        ];
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
