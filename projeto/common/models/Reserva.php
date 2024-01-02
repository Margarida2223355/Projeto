<?php

namespace common\models;

use backend\models\Imposto;
use DateTime;
use Yii;

/**
 * This is the model class for table "reserva".
 *
 * @property int $id
 * @property int $quarto_id
 * @property int $cliente_id
 * @property string $data_inicial
 * @property string $data_final
 * @property float $preco_total
 * @property string $status

 * @property User $cliente
 * @property LinhaFatura[] $linhaFaturas
 * @property Quarto $quarto
 */
class Reserva extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'reserva';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['quarto_id', 'cliente_id', 'data_inicial', 'data_final', 'preco_total', 'status'], 'required'],
            [['quarto_id', 'cliente_id'], 'integer'],
            [['data_inicial', 'data_final'], 'safe'],
            [['data_final'], 'compare', 'compareAttribute' => 'data_inicial', 'operator' => '>=', 'message' => 'A data final deve ser igual ou posterior à data inicial.'],
            [['preco_total'], 'number'],
            [['status'], 'string'],
            [['quarto_id'], 'exist', 'skipOnError' => true, 'targetClass' => Quarto::class, 'targetAttribute' => ['quarto_id' => 'id']],
            [['cliente_id'], 'exist', 'skipOnError' => true, 'targetClass' => User::class, 'targetAttribute' => ['cliente_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'quarto_id' => 'Quarto ID',
            'cliente_id' => 'Cliente ID',
            'data_inicial' => 'Data Inicial',
            'data_final' => 'Data Final',
            'preco_total' => 'Preco Total',
            'status' => 'Status',
        ];
    }


    /**
     * Gets query for [[Cliente]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCliente()
    {
        return $this->hasOne(Infuser::class, ['id' => 'cliente_id']);
    }

    /**
     * Gets query for [[LinhaFaturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFaturas()
    {
        return $this->hasMany(LinhaFatura::class, ['reserva_id' => 'id']);
    }

    /**
     * Gets query for [[Quarto]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getQuarto()
    {
        return $this->hasOne(Quarto::class, ['id' => 'quarto_id']);
    }
    
    public function calcularTotal()
    {
        $linhaFaturas = $this->getLinhaFaturas()->all();
        
        $preco_total = $this->calculaDiarias();

        foreach($linhaFaturas as $linhaFatura){
            $preco_total += $linhaFatura->sub_total;
        }
        return $preco_total;
    }
    public function calculaDiarias()
    {
        // Converta as strings de data para objetos DateTime
        $dataInicialDT = new DateTime($this->data_inicial);
        $dataFinalDT = new DateTime($this->data_final);
        // Calcule a diferença entre as duas datas
        $diferenca = $dataInicialDT->diff($dataFinalDT);
        // Acesse o número de dias a partir do objeto DateInterval
        $numDias = $diferenca->days;

       return $preco_diarias = $numDias * $this->quarto->preco;
    }
    public static function getTotalReservas(){
        return static::find()->count();
    }
}