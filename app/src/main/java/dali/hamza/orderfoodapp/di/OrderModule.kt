package dali.hamza.orderfoodapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dali.hamza.core.repository.OrderRepository
import dali.hamza.domain.repository.IOrderRepository


@Module
@InstallIn(
    ActivityComponent::class,
    FragmentComponent::class,
    ViewModelComponent::class
)
object OrderModule {

    @Provides
    fun provideOrderRepository(repository: OrderRepository): IOrderRepository =
        repository
}