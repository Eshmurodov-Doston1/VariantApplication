package uz.dostonbek.variantapplication.ui.mainView.view.chat

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.databinding.FragmentChatListBinding
import uz.dostonbek.variantapplication.models.getApplications.DataApplication
import uz.dostonbek.variantapplication.ui.baseFragment.BaseFragment
import uz.dostonbek.variantapplication.vm.statementVm.StatementVm
import uz.dostonbek.variantapplication.adapters.chatListAdapter.ChatListAdapter
import uz.dostonbek.variantapplication.utils.AppConstant.DATAAPPLICATION
import uz.dostonbek.variantapplication.utils.fetchResult

@AndroidEntryPoint
class ChatListFragment : BaseFragment(R.layout.fragment_chat_list) {
    private val binding: FragmentChatListBinding by viewBinding()
    private val statementVM: StatementVm by viewModels()
    lateinit var chatListAdapter: ChatListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            collapsing.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
            collapsing.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
            chatListAdapter = ChatListAdapter(requireContext(),object: ChatListAdapter.OnItemClickListener{
                override fun onItemClick(dataApplication: DataApplication, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable(DATAAPPLICATION,dataApplication)
                    findNavController().navigate(R.id.chatFragment,bundle)
                }
            })

            statementVM.getAllApplications()
            launch {
                statementVM.getAllApplications.fetchResult(compositionRoot.uiControllerApp,{ result->
                    if (result?.data?.isEmpty() == true){
                        animationView.visibility = View.VISIBLE
                        loadingText.visibility = View.VISIBLE
                        rvStatement.visibility  =View.GONE
                    }else{
                        animationView.visibility = View.GONE
                        rvStatement.visibility  =View.VISIBLE
                        loadingText.visibility = View.GONE
                        chatListAdapter.submitList(result?.data)
                    }
                    rvStatement.adapter = chatListAdapter
                },{isClick -> isClick })
            }
        }
    }



}