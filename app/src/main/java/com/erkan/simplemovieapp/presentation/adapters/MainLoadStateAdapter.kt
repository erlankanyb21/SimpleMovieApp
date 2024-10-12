package com.erkan.simplemovieapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erkan.simplemovieapp.databinding.ItemErrorBinding
import com.erkan.simplemovieapp.databinding.ItemProgressBinding

/**
 * [MainLoadStateAdapter] - Адаптер для обработки состояний загрузки данных внутри RecyclerView.
 *
 * @author Erlan
 * @since 1.0v
 */
class MainLoadStateAdapter : LoadStateAdapter<MainLoadStateAdapter.ItemViewHolder>() {

    /**
     * [getStateViewType] - Возвращает тип View для заданного состояния загрузки.
     * @param loadState Состояние загрузки.
     * @return Тип View для заданного состояния загрузки.
     */
    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    /**
     * [onBindViewHolder] - Вызывается при привязке ViewHolder к заданному состоянию загрузки.
     * @param holder ViewHolder для привязки.
     * @param loadState Состояние загрузки.
     */
    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    /**
     * [onCreateViewHolder] - Вызывается при создании нового ViewHolder для заданного состояния загрузки.
     * @param parent Родительский ViewGroup.
     * @param loadState Состояние загрузки.
     * @return ViewHolder для заданного состояния загрузки.
     */
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    /**
     * [ItemViewHolder] - ViewHolder для каждого типа состояния загрузки.
     */
    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        /**
         * [bind] - Привязывает состояние загрузки к ViewHolder.
         * @param loadState Состояние загрузки.
         */
        abstract fun bind(loadState: LoadState)
    }

    /**
     * [ProgressViewHolder] - ViewHolder для отображения состояния загрузки в процессе.
     * @property binding Связывает представление ViewHolder с данными.
     */
    class ProgressViewHolder internal constructor(
        binding: ItemProgressBinding
    ) : ItemViewHolder(binding.root) {

        /**
         * [bind] - Связывает ViewHolder с заданным состоянием загрузки.
         * @param loadState Состояние загрузки.
         */
        override fun bind(loadState: LoadState) {
            // Do nothing
        }

        companion object {

            /**
             * [invoke] - Создает новый ViewHolder для отображения состояния загрузки в процессе.
             * @param layoutInflater Надувает макет.
             * @param parent Родительский ViewGroup.
             * @param attachToRoot True, если макет должен быть связан с родительским ViewGroup, иначе false.
             * @return Созданный ViewHolder.
             */
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ProgressViewHolder {
                return ProgressViewHolder(
                    ItemProgressBinding.inflate(
                        layoutInflater, parent, attachToRoot
                    )
                )
            }
        }
    }

    /**
     * [ErrorViewHolder] - ViewHolder для отображения состояния ошибки в процессе.
     * @property binding Связывает представление ViewHolder с данными.
     */
    class ErrorViewHolder internal constructor(
        private val binding: ItemErrorBinding
    ) : ItemViewHolder(binding.root) {

        /**
         * [bind] - Связывает ViewHolder с заданным состоянием ошибки.
         * @param loadState Состояние загрузки.
         *
         * в TextView errorMessage присваивается значение из [LoadState.Error]
         */
        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
        }

        companion object {

            /**
             * [invoke] - Создает новый ViewHolder для отображения состояния ошибки в процессе.
             * @param layoutInflater инфлейтит макет.
             * @param parent Родительский ViewGroup.
             * @param attachToRoot True, если макет должен быть связан с родительским ViewGroup, иначе false.
             * @return Созданный ViewHolder.
             */
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ErrorViewHolder {
                return ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        layoutInflater, parent, attachToRoot
                    )
                )
            }
        }
    }
}