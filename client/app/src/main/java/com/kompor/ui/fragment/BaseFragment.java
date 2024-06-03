package com.kompor.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewbinding.ViewBinding;

abstract class BaseFragment <C, B extends ViewBinding> extends Fragment {
    protected B binding;
    protected C controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        return binding.getRoot();
    }

    abstract B getFragmentBinding(LayoutInflater inflater, ViewGroup container);
    abstract Class<C> getController();
}
